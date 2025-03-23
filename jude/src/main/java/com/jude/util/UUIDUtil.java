package com.jude.util;

import java.util.UUID;

public class UUIDUtil {
    /**
     * 生成UUID
     * @return
     */
    public static String generateUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
