package com.incheonilbo.lolanalysis.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "lolanalysis")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@AllArgsConstructor
public class GameData {

    @EmbeddedId
    private GameDataId gameDataId;

    @Column(name = "GAMEDURATION")
    private String duration;
    @Column(name = "GAMEVERSION")
    private String version;

    @Column(name = "CHAMPIONID")
    private Integer championId;

    @Column(name = "TEAMPOSITION")
    private String lane;

    @Column(name = "TEAMID")
    private int side;

    private String win;
    private int kills;
    private int deaths;
    private int assists;
    @Column(name = "DAMAGEDEALT")
    private int damageDealt;

    @Column(name = "DAMAGETAKEN")
    private int damageTaken;

    private String skill5History;
    private String skill10History;
    private String skill15History;

}
