package com.incheonilbo.lolanalysis.repository.query;

import com.incheonilbo.lolanalysis.dto.ForChemChamp;
import com.incheonilbo.lolanalysis.entity.QGameData;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.incheonilbo.lolanalysis.entity.QGameData.gameData;

@Repository
@RequiredArgsConstructor
public class GameDataQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    final String WIN_VALUE = "True";
    QGameData chemChamp1 = new QGameData("chemChamp1");
    QGameData chemChamp2 = new QGameData("chemChamp2");
    QGameData chemChamp3 = new QGameData("chemChamp3");
    QGameData chemChamp4 = new QGameData("chemChamp4");

    public List<ForChemChamp> getForChemChampsAboutWin(Integer chemChampionIdCond, String laneCond, List<Integer> chemChampionIdsCond, List<String> chemLanesCond) {
        switch (chemChampionIdsCond.size()) {
            default:
                return getForChemChampsAboutWinByListZero(chemChampionIdCond, laneCond);
            case 1:
                return getForChemChampsAboutWinByListOne(chemChampionIdCond, laneCond, chemChampionIdsCond, chemLanesCond);
            case 2:
                return getForChemChampsAboutWinByListTwo(chemChampionIdCond, laneCond, chemChampionIdsCond, chemLanesCond);
            case 3:
                return getForChemChampsAboutWinByListThree(chemChampionIdCond, laneCond, chemChampionIdsCond, chemLanesCond);
        }
    }

    public List<ForChemChamp> getForChemChamps(Integer chemChampionIdCond, String laneCond, List<Integer> chemChampionIdsCond, List<String> chemLanesCond) {
        switch (chemChampionIdsCond.size()) {
            default:
                return getForChemChampsByListZero(chemChampionIdCond, laneCond);
            case 1:
                return getForChemChampsByListOne(chemChampionIdCond, laneCond, chemChampionIdsCond, chemLanesCond);
            case 2:
                return getForChemChampsByListTwo(chemChampionIdCond, laneCond, chemChampionIdsCond, chemLanesCond);
            case 3:
                return getForChemChampsByListThree(chemChampionIdCond, laneCond, chemChampionIdsCond, chemLanesCond);
        }
    }

    private List<ForChemChamp> getForChemChampsAboutWinByListZero(Integer chemChampionIdCond, String laneCond) {
        return jpaQueryFactory
                .select(Projections.fields(ForChemChamp.class,
                        gameData.championId,
                        gameData.lane,
                        Wildcard.count.as("amountOfGame")))
                .from(gameData)
                .join(chemChamp1).on(
                        chemChamp1.championId.eq(chemChampionIdCond),
                        chemChamp1.lane.eq(laneCond),
                        chemChamp1.win.eq(WIN_VALUE),
                        gameData.lane.ne(laneCond),
                        gameData.side.eq(chemChamp1.side),
                        gameData.gameDataId.gameId.eq(chemChamp1.gameDataId.gameId))
                .groupBy(gameData.championId, gameData.lane)
                .fetch();
    }
    private List<ForChemChamp> getForChemChampsAboutWinByListOne(Integer chemChampionIdCond, String laneCond, List<Integer> chemChampionIdsCond, List<String> chemLanesCond) {
        return jpaQueryFactory
                .select(Projections.fields(ForChemChamp.class,
                        gameData.championId,
                        gameData.lane,
                        Wildcard.count.as("amountOfGame")))
                .from(gameData)
                .join(chemChamp1).on(
                        chemChamp1.championId.eq(chemChampionIdCond),
                        chemChamp1.lane.eq(laneCond),
                        chemChamp1.win.eq(WIN_VALUE),
                        gameData.lane.ne(laneCond),
                        gameData.side.eq(chemChamp1.side),
                        gameData.gameDataId.gameId.eq(chemChamp1.gameDataId.gameId))
                .join(chemChamp2).on(
                        chemChamp2.championId.eq(chemChampionIdsCond.get(0)),
                        chemChamp2.lane.eq(chemLanesCond.get(0)),
                        gameData.lane.ne(chemLanesCond.get(0)),
                        chemChamp2.win.eq(WIN_VALUE),
                        chemChamp2.side.eq(chemChamp1.side),
                        chemChamp2.gameDataId.gameId.eq(chemChamp1.gameDataId.gameId))
                .groupBy(gameData.championId, gameData.lane)
                .fetch();
    }
    private List<ForChemChamp> getForChemChampsAboutWinByListTwo(Integer chemChampionIdCond, String laneCond, List<Integer> chemChampionIdsCond, List<String> chemLanesCond) {
        return jpaQueryFactory
                .select(Projections.fields(ForChemChamp.class,
                        gameData.championId,
                        gameData.lane,
                        Wildcard.count.as("amountOfGame")))
                .from(gameData)
                .join(chemChamp1).on(
                        chemChamp1.championId.eq(chemChampionIdCond),
                        chemChamp1.lane.eq(laneCond),
                        chemChamp1.win.eq(WIN_VALUE),
                        gameData.lane.ne(laneCond),
                        gameData.side.eq(chemChamp1.side),
                        gameData.gameDataId.gameId.eq(chemChamp1.gameDataId.gameId))
                .join(chemChamp2).on(
                        chemChamp2.championId.eq(chemChampionIdsCond.get(0)),
                        chemChamp2.lane.eq(chemLanesCond.get(0)),
                        gameData.lane.ne(chemLanesCond.get(0)),
                        chemChamp2.win.eq(WIN_VALUE),
                        chemChamp2.side.eq(chemChamp1.side),
                        chemChamp2.gameDataId.gameId.eq(chemChamp1.gameDataId.gameId))
                .join(chemChamp3).on(
                        chemChamp3.championId.eq(chemChampionIdsCond.get(1)),
                        chemChamp3.lane.eq(chemLanesCond.get(1)),
                        gameData.lane.ne(chemLanesCond.get(1)),
                        chemChamp3.win.eq(WIN_VALUE),
                        chemChamp3.side.eq(chemChamp1.side),
                        chemChamp3.gameDataId.gameId.eq(chemChamp1.gameDataId.gameId))
                .groupBy(gameData.championId, gameData.lane)
                .fetch();
    }
    private List<ForChemChamp> getForChemChampsAboutWinByListThree(Integer chemChampionIdCond, String laneCond, List<Integer> chemChampionIdsCond, List<String> chemLanesCond) {
        return jpaQueryFactory
                .select(Projections.fields(ForChemChamp.class,
                        gameData.championId,
                        gameData.lane,
                        Wildcard.count.as("amountOfGame")))
                .from(gameData)
                .join(chemChamp1).on(
                        chemChamp1.championId.eq(chemChampionIdCond),
                        chemChamp1.lane.eq(laneCond),
                        chemChamp1.win.eq(WIN_VALUE),
                        gameData.lane.ne(laneCond),
                        gameData.side.eq(chemChamp1.side),
                        gameData.gameDataId.gameId.eq(chemChamp1.gameDataId.gameId))
                .join(chemChamp2).on(
                        chemChamp2.championId.eq(chemChampionIdsCond.get(0)),
                        chemChamp2.lane.eq(chemLanesCond.get(0)),
                        gameData.lane.ne(chemLanesCond.get(0)),
                        chemChamp2.win.eq(WIN_VALUE),
                        chemChamp2.side.eq(chemChamp1.side),
                        chemChamp2.gameDataId.gameId.eq(chemChamp1.gameDataId.gameId))
                .join(chemChamp3).on(
                        chemChamp3.championId.eq(chemChampionIdsCond.get(1)),
                        chemChamp3.lane.eq(chemLanesCond.get(1)),
                        gameData.lane.ne(chemLanesCond.get(1)),
                        chemChamp3.win.eq(WIN_VALUE),
                        chemChamp3.side.eq(chemChamp1.side),
                        chemChamp3.gameDataId.gameId.eq(chemChamp1.gameDataId.gameId))
                .join(chemChamp4).on(
                        chemChamp4.championId.eq(chemChampionIdsCond.get(2)),
                        chemChamp4.lane.eq(chemLanesCond.get(2)),
                        gameData.lane.ne(chemLanesCond.get(2)),
                        chemChamp4.win.eq(WIN_VALUE),
                        chemChamp4.side.eq(chemChamp1.side),
                        chemChamp4.gameDataId.gameId.eq(chemChamp1.gameDataId.gameId))
                .groupBy(gameData.championId, gameData.lane)
                .fetch();
    }

    public List<ForChemChamp> getForChemChampsByListZero(Integer chemChampionIdCond, String laneCond) {
        return jpaQueryFactory
                .select(Projections.fields(ForChemChamp.class,
                        gameData.championId,
                        gameData.lane,
                        Wildcard.count.as("amountOfGame")))
                .from(gameData)
                .join(chemChamp1).on(
                        chemChamp1.championId.eq(chemChampionIdCond),
                        chemChamp1.lane.eq(laneCond),
                        gameData.lane.ne(laneCond),
                        gameData.side.eq(chemChamp1.side),
                        gameData.gameDataId.gameId.eq(chemChamp1.gameDataId.gameId))
                .groupBy(gameData.championId, gameData.lane)
                .fetch();
    }
    public List<ForChemChamp> getForChemChampsByListOne(Integer chemChampionIdCond, String laneCond, List<Integer> chemChampionIdsCond, List<String> chemLanesCond) {
        return jpaQueryFactory
                .select(Projections.fields(ForChemChamp.class,
                        gameData.championId,
                        gameData.lane,
                        Wildcard.count.as("amountOfGame")))
                .from(gameData)
                .join(chemChamp1).on(
                        chemChamp1.championId.eq(chemChampionIdCond),
                        chemChamp1.lane.eq(laneCond),
                        gameData.lane.ne(laneCond),
                        gameData.side.eq(chemChamp1.side),
                        gameData.gameDataId.gameId.eq(chemChamp1.gameDataId.gameId))
                .join(chemChamp2).on(
                        chemChamp2.championId.eq(chemChampionIdsCond.get(0)),
                        chemChamp2.lane.eq(chemLanesCond.get(0)),
                        gameData.lane.ne(chemLanesCond.get(0)),
                        chemChamp2.side.eq(chemChamp1.side),
                        chemChamp2.gameDataId.gameId.eq(chemChamp1.gameDataId.gameId))
                .groupBy(gameData.championId, gameData.lane)
                .fetch();
    }
    public List<ForChemChamp> getForChemChampsByListTwo(Integer chemChampionIdCond, String laneCond, List<Integer> chemChampionIdsCond, List<String> chemLanesCond) {
        return jpaQueryFactory
                .select(Projections.fields(ForChemChamp.class,
                        gameData.championId,
                        gameData.lane,
                        Wildcard.count.as("amountOfGame")))
                .from(gameData)
                .join(chemChamp1).on(
                        chemChamp1.championId.eq(chemChampionIdCond),
                        chemChamp1.lane.eq(laneCond),
                        gameData.lane.ne(laneCond),
                        gameData.side.eq(chemChamp1.side),
                        gameData.gameDataId.gameId.eq(chemChamp1.gameDataId.gameId))
                .join(chemChamp2).on(
                        chemChamp2.championId.eq(chemChampionIdsCond.get(0)),
                        chemChamp2.lane.eq(chemLanesCond.get(0)),
                        gameData.lane.ne(chemLanesCond.get(0)),
                        chemChamp2.side.eq(chemChamp1.side),
                        chemChamp2.gameDataId.gameId.eq(chemChamp1.gameDataId.gameId))
                .join(chemChamp3).on(
                        chemChamp3.championId.eq(chemChampionIdsCond.get(1)),
                        chemChamp3.lane.eq(chemLanesCond.get(1)),
                        gameData.lane.ne(chemLanesCond.get(1)),
                        chemChamp3.side.eq(chemChamp1.side),
                        chemChamp3.gameDataId.gameId.eq(chemChamp1.gameDataId.gameId))
                .groupBy(gameData.championId, gameData.lane)
                .fetch();
    }
    public List<ForChemChamp> getForChemChampsByListThree(Integer chemChampionIdCond, String laneCond, List<Integer> chemChampionIdsCond, List<String> chemLanesCond) {
        return jpaQueryFactory
                .select(Projections.fields(ForChemChamp.class,
                        gameData.championId,
                        gameData.lane,
                        Wildcard.count.as("amountOfGame")))
                .from(gameData)
                .join(chemChamp1).on(
                        chemChamp1.championId.eq(chemChampionIdCond),
                        chemChamp1.lane.eq(laneCond),
                        gameData.lane.ne(laneCond),
                        gameData.side.eq(chemChamp1.side),
                        gameData.gameDataId.gameId.eq(chemChamp1.gameDataId.gameId))
                .join(chemChamp2).on(
                        chemChamp2.championId.eq(chemChampionIdsCond.get(0)),
                        chemChamp2.lane.eq(chemLanesCond.get(0)),
                        gameData.lane.ne(chemLanesCond.get(0)),
                        chemChamp2.side.eq(chemChamp1.side),
                        chemChamp2.gameDataId.gameId.eq(chemChamp1.gameDataId.gameId))
                .join(chemChamp3).on(
                        chemChamp3.championId.eq(chemChampionIdsCond.get(1)),
                        chemChamp3.lane.eq(chemLanesCond.get(1)),
                        gameData.lane.ne(chemLanesCond.get(1)),
                        chemChamp3.side.eq(chemChamp1.side),
                        chemChamp3.gameDataId.gameId.eq(chemChamp1.gameDataId.gameId))
                .join(chemChamp4).on(
                        chemChamp4.championId.eq(chemChampionIdsCond.get(2)),
                        chemChamp4.lane.eq(chemLanesCond.get(2)),
                        gameData.lane.ne(chemLanesCond.get(2)),
                        chemChamp4.side.eq(chemChamp1.side),
                        chemChamp4.gameDataId.gameId.eq(chemChamp1.gameDataId.gameId))
                .groupBy(gameData.championId, gameData.lane)
                .fetch();
    }
}
