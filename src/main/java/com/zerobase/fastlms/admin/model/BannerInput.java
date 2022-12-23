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

    private String idList; // 삭제 요청하는 배너들의 리스트가 , 로 이어져있다

}
