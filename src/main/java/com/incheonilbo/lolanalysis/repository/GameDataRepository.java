package com.incheonilbo.lolanalysis.repository;

import com.incheonilbo.lolanalysis.entity.GameData;
import com.incheonilbo.lolanalysis.entity.GameDataId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameDataRepository extends JpaRepository<GameData, GameDataId> {
}
