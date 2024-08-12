package com.duong.SpringLinhTinh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duong.SpringLinhTinh.entity.Permission;
@Repository
public interface PermissionRepository extends JpaRepository<Permission,String> {
}
