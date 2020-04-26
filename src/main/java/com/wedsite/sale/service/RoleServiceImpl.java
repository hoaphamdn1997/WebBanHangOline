package com.wedsite.sale.service;


import com.wedsite.sale.entity.RoleEntity;
import com.wedsite.sale.service.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * The type Role service.
 */
@Service
public class RoleServiceImpl implements RoleService {

    /**
     * The Role repository.
     */
    @Autowired
    RoleRepository roleRepository;

    /**
     * Find All Role
     *
     * @return List Role
     */
    @Override
    public List<RoleEntity> findAll() {
        return roleRepository.findAll();
    }

}
