package com.tasksmangment.pfa.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tasksmangment.pfa.models.Role;
import com.tasksmangment.pfa.models.RoleName;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	// Optional class qui permet d’encapsuler un objet dont la valeur peut être null
		Optional<Role> findByName(RoleName roleName);
	}

