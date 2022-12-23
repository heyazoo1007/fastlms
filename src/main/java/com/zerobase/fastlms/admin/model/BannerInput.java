package com.zerobase.fastlms.admin.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BannerInput extends CommonParameter {
    private long id;
    private String bannerName;
    private String linkAddress;
    private String sortSequence;

    private String saveFilename;
    private String urlFilename;
    private boolean publicOrNot;

}
