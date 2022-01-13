package com.incheonilbo.lolanalysis.repository.query;

import com.incheonilbo.lolanalysis.dto.ForChampsTable;
import com.incheonilbo.lolanalysis.dto.ForSkillFormat;
import com.incheonilbo.lolanalysis.entity.QGameData;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.incheonilbo.lolanalysis.entity.QGameData.gameData;

@Repository
@RequiredArgsConstructor
public class ChampTableRepository {

    private final JPAQueryFactory jpaQueryFactory;
    final String WIN_VALUE = "True";
    final Integer GAME_LIMIT = 100;

    public List<ForChampsTable> findChampsByLane(String laneCond) {
        return jpaQueryFactory
                .select(Projections.fields(ForChampsTable.class,
                        gameData.championId,
                        Wildcard.count.as("amountOfGame")))
                .from(gameData)
                .where(gameData.lane.eq(laneCond))
                .groupBy(gameData.championId)
                .having(Wildcard.count.goe(GAME_LIMIT))
                .fetch();
    }

    public List<ForChampsTable> findChampsWinByLane(String laneCond) {
        return jpaQueryFactory
                .select(Projections.fields(ForChampsTable.class,
                        gameData.championId,
                        Wildcard.count.as("amountOfGame")))
                .from(gameData)
                .where(gameData.lane.eq(laneCond),
                        gameData.win.eq(WIN_VALUE))
                .groupBy(gameData.championId)
                .having(Wildcard.count.goe(GAME_LIMIT))
                .fetch();
    }

    public Long findTotalCountByLane(String laneCond) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(gameData)
                .where(gameData.lane.eq(laneCond))
                .fetchOne();
    }
}
