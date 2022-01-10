package com.incheonilbo.lolanalysis.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@Getter
@Setter
public class GameDataId implements Serializable {
    @Column(name = "GAMEID")
    private Long gameId;

    @Column(name = "PARTICIPANTID")
    private int inGameNumber;
}
