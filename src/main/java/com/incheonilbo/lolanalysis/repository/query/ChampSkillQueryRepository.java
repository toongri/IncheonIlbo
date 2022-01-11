package com.incheonilbo.lolanalysis.repository.query;

import com.incheonilbo.lolanalysis.dto.ForChemChamp;
import com.incheonilbo.lolanalysis.dto.ForSkillAllCountAndWinCount;
import com.incheonilbo.lolanalysis.dto.ForSkillFormat;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.incheonilbo.lolanalysis.entity.QGameData.gameData;

@Repository
@RequiredArgsConstructor
public class ChampSkillQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    final String WIN_VALUE = "True";

    public List<ForSkillFormat> getAmountOfGameAboutSkillDataIndex0(Integer championIdCond, String laneCond) {
        return jpaQueryFactory
                .select(Projections.fields(ForSkillFormat.class,
                        gameData.skill5History.as("skillCombination"),
                        Wildcard.count.as("amountOfGame")))
                .from(gameData)
                .where(
                        gameData.championId.eq(championIdCond),
                        gameData.lane.eq(laneCond))
                .groupBy(gameData.skill5History)
                .having(gameData.skill5History.isNotEmpty())
                .fetch();
    }
    public List<ForSkillFormat> getAmountOfGameAboutSkillDataIndex1(Integer championIdCond, String laneCond) {
        return jpaQueryFactory
                .select(Projections.fields(ForSkillFormat.class,
                        gameData.skill10History.as("skillCombination"),
                        Wildcard.count.as("amountOfGame")))
                .from(gameData)
                .where(
                        gameData.championId.eq(championIdCond),
                        gameData.lane.eq(laneCond))
                .groupBy(gameData.skill10History)
                .having(gameData.skill10History.isNotEmpty())
                .fetch();
    }
    public List<ForSkillFormat> getAmountOfGameAboutSkillDataIndex2(Integer championIdCond, String laneCond) {
        return jpaQueryFactory
                .select(Projections.fields(ForSkillFormat.class,
                        gameData.skill15History.as("skillCombination"),
                        Wildcard.count.as("amountOfGame")))
                .from(gameData)
                .where(
                        gameData.championId.eq(championIdCond),
                        gameData.lane.eq(laneCond))
                .groupBy(gameData.skill15History)
                .having(gameData.skill15History.isNotEmpty())
                .fetch();
    }

    public List<ForSkillFormat> getAmountOfGameAboutWinSkillDataIndex0(Integer championIdCond, String laneCond) {
        return jpaQueryFactory
                .select(Projections.fields(ForSkillFormat.class,
                        gameData.skill5History.as("skillCombination"),
                        Wildcard.count.as("amountOfGame")))
                .from(gameData)
                .where(
                        gameData.championId.eq(championIdCond),
                        gameData.lane.eq(laneCond),
                        gameData.win.eq(WIN_VALUE))
                .groupBy(gameData.skill5History)
                .having(gameData.skill5History.isNotEmpty())
                .fetch();
    }
    public List<ForSkillFormat> getAmountOfGameAboutWinSkillDataIndex1(Integer championIdCond, String laneCond) {
        return jpaQueryFactory
                .select(Projections.fields(ForSkillFormat.class,
                        gameData.skill10History.as("skillCombination"),
                        Wildcard.count.as("amountOfGame")))
                .from(gameData)
                .where(
                        gameData.championId.eq(championIdCond),
                        gameData.lane.eq(laneCond),
                        gameData.win.eq(WIN_VALUE))
                .groupBy(gameData.skill10History)
                .having(gameData.skill10History.isNotEmpty())
                .fetch();
    }
    public List<ForSkillFormat> getAmountOfGameAboutWinSkillDataIndex2(Integer championIdCond, String laneCond) {
        return jpaQueryFactory
                .select(Projections.fields(ForSkillFormat.class,
                        gameData.skill15History.as("skillCombination"),
                        Wildcard.count.as("amountOfGame")))
                .from(gameData)
                .where(
                        gameData.championId.eq(championIdCond),
                        gameData.lane.eq(laneCond),
                        gameData.win.eq(WIN_VALUE))
                .groupBy(gameData.skill15History)
                .having(gameData.skill15History.isNotEmpty())
                .fetch();
    }
}
