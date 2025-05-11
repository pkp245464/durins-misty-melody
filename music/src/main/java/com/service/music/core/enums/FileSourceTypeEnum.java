package com.service.music.core.enums;

public enum FileSourceTypeEnum {
    USER_PROFILE_PHOTO,
    COVER_IMAGE,
    MUSIC_FILE;
    public static boolean isValidSource(String source) {
        try {
            FileSourceTypeEnum.valueOf(source);
            return true;
        }
        catch (IllegalArgumentException e) {
            return false;
        }
    }
}
