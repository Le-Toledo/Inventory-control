package com.inventory.repository

import com.inventory.entity.RawMaterial
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RawMaterialRepository : JpaRepository<RawMaterial, Long>
