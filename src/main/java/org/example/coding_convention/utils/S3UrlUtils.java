package org.example.coding_convention.utils;

import java.net.URI;

public final class S3UrlUtils {
    private S3UrlUtils() {}

    public static record Parts(String bucket, String key) {}

    public static Parts parse(String urlOrS3uri) {
        if (urlOrS3uri == null || urlOrS3uri.isBlank()) {
            throw new IllegalArgumentException("S3 URL이 비었습니다.");
        }

        if (urlOrS3uri.startsWith("s3://")) {
            String raw = urlOrS3uri.substring("s3://".length()); // bucket/key...
            int slash = raw.indexOf('/');
            if (slash < 0) throw new IllegalArgumentException("s3:// 형식에 key가 없습니다: " + urlOrS3uri);
            return new Parts(raw.substring(0, slash), raw.substring(slash + 1));
        }

        // https:// 버전 (가상호스트/패스형 모두 대응)
        URI u = URI.create(urlOrS3uri);
        String host = u.getHost();            // e.g. aws-junsun-s3.s3.amazonaws.com  or  s3.ap-northeast-2.amazonaws.com
        String path = u.getPath();            // e.g. /20250819/uuid_five.txt  or  /bucket/20250819/uuid...

        if (host == null) throw new IllegalArgumentException("잘못된 URL: " + urlOrS3uri);

        if (host.startsWith("s3.")) {
            // path-style: https://s3.ap-xxx.amazonaws.com/{bucket}/{key}
            String p = path.startsWith("/") ? path.substring(1) : path;
            int slash = p.indexOf('/');
            if (slash < 0) throw new IllegalArgumentException("경로에 bucket/key가 없습니다: " + urlOrS3uri);
            String bucket = p.substring(0, slash);
            String key = p.substring(slash + 1);
            return new Parts(bucket, key);
        }

        // virtual-hosted-style: https://{bucket}.s3.amazonaws.com/{key}  또는 {bucket}.s3.ap-xxx.amazonaws.com
        int dot = host.indexOf(".s3");
        if (dot < 0) throw new IllegalArgumentException("S3 도메인이 아님: " + urlOrS3uri);
        String bucket = host.substring(0, dot);
        String key = path.startsWith("/") ? path.substring(1) : path;

        return new Parts(bucket, key);
    }
}

