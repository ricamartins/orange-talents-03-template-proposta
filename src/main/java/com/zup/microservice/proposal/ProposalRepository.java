package com.zup.microservice.proposal;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zup.microservice.proposal.Proposal.ProposalStatus;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Long> {

	Optional<Proposal> findByDocument(String document);

	List<Proposal> findAllByStatusAndCardIsNull(ProposalStatus status);
}
