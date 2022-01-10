package com.incheonilbo.lolanalysis.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "data_solo_rank")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class GameData {

    @EmbeddedId
    private GameDataId gameDataId;

    private int duration;
    private double version;

    @Column(name = "CHAMP_CHAMPIONID")
    private Integer championId;

    private String lane;
    private String side;
    private String win;

    @Column(name = "DAMAGEDEALT")
    private int damageDealt;

    @Column(name = "DAMAGETAKEN")
    private int damageTaken;

    private int gold;
    private int kills;
    private int deaths;
    private int assists;
    private int G5;
    private int G6;
    private int G7;
    private int G8;
    private int G9;
    private int G10;
    private int G11;
    private int G12;
    private int G13;
    private int G14;
    private int G15;
    private int G16;
    private int G17;
    private int G18;
    private int G19;
    private int G20;
    private int G21;
    private int G22;
    private int G23;
    private int G24;
    private int G25;
    private int G26;
    private int G27;
    private int G28;
    private int G29;
    private int G30;

    public GameData(GameDataId gameDataId, int duration, double version, Integer championId, String lane, String side, String win, int damageDealt, int damageTaken, int gold, int kills, int deaths, int assists, int g5, int g6, int g7, int g8, int g9, int g10, int g11, int g12, int g13, int g14, int g15, int g16, int g17, int g18, int g19, int g20, int g21, int g22, int g23, int g24, int g25, int g26, int g27, int g28, int g29, int g30) {
        this.gameDataId = gameDataId;
        this.duration = duration;
        this.version = version;
        this.lane = lane;
        this.side = side;
        this.win = win;
        this.damageDealt = damageDealt;
        this.damageTaken = damageTaken;
        this.gold = gold;
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        G5 = g5;
        G6 = g6;
        G7 = g7;
        G8 = g8;
        G9 = g9;
        G10 = g10;
        G11 = g11;
        G12 = g12;
        G13 = g13;
        G14 = g14;
        G15 = g15;
        G16 = g16;
        G17 = g17;
        G18 = g18;
        G19 = g19;
        G20 = g20;
        G21 = g21;
        G22 = g22;
        G23 = g23;
        G24 = g24;
        G25 = g25;
        G26 = g26;
        G27 = g27;
        G28 = g28;
        G29 = g29;
        G30 = g30;
        this.championId = championId;
    }
}
