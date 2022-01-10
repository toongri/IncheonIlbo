package com.incheonilbo.lolanalysis.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "info_champion")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Champ {
    @Id
    @Column(name = "CHAMPIONID")
    private Integer championId;//championId
    @Column(name = "CHAMPION_NAME_KR")
    private String nameOfChamp;

    public Champ(int championId, String nameOfChamp) {
        this.championId = championId;
        this.nameOfChamp = nameOfChamp;
    }
}
