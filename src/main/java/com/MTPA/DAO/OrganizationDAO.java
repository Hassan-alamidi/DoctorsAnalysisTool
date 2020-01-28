package com.MTPA.DAO;

import com.MTPA.Objects.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//this object represents a medical institute like GP,Hospital etc.
@Repository
public interface OrganizationDAO extends JpaRepository<Organization, Integer> {
}
