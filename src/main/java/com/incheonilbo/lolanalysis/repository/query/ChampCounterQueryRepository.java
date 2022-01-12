package com.incheonilbo.lolanalysis.repository.query;

import com.incheonilbo.lolanalysis.dto.ForChemChamp;
import com.incheonilbo.lolanalysis.dto.ForCounter5Champ;
import com.incheonilbo.lolanalysis.entity.GameData;
import com.incheonilbo.lolanalysis.entity.QChamp;
import com.incheonilbo.lolanalysis.entity.QGameData;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.incheonilbo.lolanalysis.entity.QChamp.champ;
import static com.incheonilbo.lolanalysis.entity.QGameData.gameData;

@Repository
@RequiredArgsConstructor
public class ChampCounterQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    final String WIN_VALUE = "True";
    QGameData compareChamp = new QGameData("compareChamp");

    public List<ForCounter5Champ> get5ChampsAboutCountersOfMainChamp(Integer championIdCond, String laneCond) {
        return jpaQueryFactory
                .select(Projections.fields(ForCounter5Champ.class,
                        gameData.championId,
                        Wildcard.count.as("amountOfGame")))
                .from(gameData)
                .join(compareChamp).on(
                        compareChamp.championId.eq(championIdCond),
                        compareChamp.lane.eq(laneCond),
                        gameData.lane.eq(laneCond),
                        gameData.side.ne(compareChamp.side),
                        gameData.gameDataId.gameId.eq(compareChamp.gameDataId.gameId))
                .groupBy(gameData.championId)
                .fetch();
    }

    public List<ForCounter5Champ> get5ChampsAboutWinCountersOfMainChamp(Integer championIdCond, String laneCond) {
        return jpaQueryFactory
                .select(Projections.fields(ForCounter5Champ.class,
                        gameData.championId,
                        Wildcard.count.as("amountOfGame")))
                .from(gameData)
                .join(compareChamp).on(
                        compareChamp.championId.eq(championIdCond),
                        compareChamp.lane.eq(laneCond),
                        gameData.lane.eq(laneCond),
                        gameData.win.eq(WIN_VALUE),
                        gameData.side.ne(compareChamp.side),
                        gameData.gameDataId.gameId.eq(compareChamp.gameDataId.gameId))
                .groupBy(gameData.championId)
                .fetch();
    }
}
