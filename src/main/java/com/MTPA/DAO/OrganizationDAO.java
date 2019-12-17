package com.MTPA.DAO;

import com.MTPA.Objects.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

//this object represents a medical institute like GP,Hospital etc.
public interface OrganizationDAO extends JpaRepository<Organization, Integer> {
}
