package com.tokokue.demo.repository;

import com.tokokue.demo.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    
    // Query untuk mencari Admin berdasarkan field nama yang ada di parent class (User)
    @Query("SELECT a FROM Admin a WHERE LOWER(a.nama) = LOWER(:nama)")
    Optional<Admin> findByNamaCaseInsensitive(@Param("nama") String nama);
}