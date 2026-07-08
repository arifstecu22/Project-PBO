package com.tokokue.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tokokue.demo.model.Pesanan;

@Repository
public interface PesananRepository extends JpaRepository<Pesanan, Integer> {
}