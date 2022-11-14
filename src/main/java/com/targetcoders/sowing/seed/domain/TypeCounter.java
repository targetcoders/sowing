package com.targetcoders.sowing.seed.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TypeCounter {
    //TODO: 유저마다 자신만의 시드 타입 목록을 관리할 수 있도록 개선이 필요합니다.
    private long playCount;
    private long studyCount;
    private long dateCount;
    private long readCount;

    public long total() {
        return playCount + studyCount + dateCount + readCount;
    }
    public void incPlay() {
        playCount += 1;
    }
    public void incStudy() {
        studyCount += 1;
    }
    public void incDate() {
        dateCount += 1;
    }
    public void incRead() {
        readCount += 1;
    }
}
