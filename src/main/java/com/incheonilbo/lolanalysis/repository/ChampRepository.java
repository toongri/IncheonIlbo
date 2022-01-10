package com.incheonilbo.lolanalysis.repository;

import com.incheonilbo.lolanalysis.entity.Champ;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChampRepository extends JpaRepository<Champ, Integer> {
}
