package com.bank.application.mapper;

import org.springframework.stereotype.Component;

import com.bank.application.dto.transaction.PendingApprovalResponseDto;
import com.bank.application.dto.transaction.TransactionHistoryResponseDto;
import com.bank.application.dto.transaction.TransactionResponseDto;
import com.bank.application.entity.Transaction;

@Component
public class TransactionMapper {
	public TransactionResponseDto toResponse(Transaction txn) {
		return new TransactionResponseDto(txn.getTransactionRef(), txn.getAccount().getAccountNumber(),
				txn.getType().name(), txn.getAmount(), txn.getStatus().name(), txn.getPerformedBy().getUsername(),
				txn.getApprovedBy() != null ? txn.getApprovedBy().getUsername() : null, txn.getCreatedAt(),
				txn.getApprovedAt());
	}

	public TransactionHistoryResponseDto toHistory(Transaction txn) {
		return new TransactionHistoryResponseDto(txn.getTransactionRef(), txn.getType().name(), txn.getAmount(),
				txn.getStatus().name(), txn.getPerformedBy().getUsername(),
				txn.getApprovedBy() != null ? txn.getApprovedBy().getUsername() : null, txn.getCreatedAt());
	}

	public PendingApprovalResponseDto toPendingApproval(Transaction txn) {
		return new PendingApprovalResponseDto(txn.getTransactionRef(), txn.getAccount().getAccountNumber(),
				txn.getAmount(), txn.getPerformedBy().getUsername(), txn.getCreatedAt());
	}
}



//package com.bank.application.mapper;
//
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.Named;
//
//import com.bank.application.dto.transaction.PendingApprovalResponseDto;
//import com.bank.application.dto.transaction.TransactionHistoryResponseDto;
//import com.bank.application.dto.transaction.TransactionResponseDto;
//import com.bank.application.entity.Transaction;
//
//@Mapper(componentModel = "spring")
//public interface TransactionMapper {
//
//    @Mapping(source = "transactionRef", target = "transactionRef")
//    @Mapping(source = "account.accountNumber", target = "accountNumber")
//    @Mapping(source = "type", target = "type", qualifiedByName = "enumToString")
//    @Mapping(source = "amount", target = "amount")
//    @Mapping(source = "status", target = "status", qualifiedByName = "enumToString")
//    @Mapping(source = "performedBy.username", target = "performedBy")
//    @Mapping(source = "approvedBy.username", target = "approvedBy")
//    @Mapping(source = "createdAt", target = "createdAt")
//    @Mapping(source = "approvedAt", target = "approvedAt")
//    TransactionResponseDto toResponse(Transaction txn);
//
//    @Mapping(source = "transactionRef", target = "transactionRef")
//    @Mapping(source = "type", target = "type", qualifiedByName = "enumToString")
//    @Mapping(source = "amount", target = "amount")
//    @Mapping(source = "status", target = "status", qualifiedByName = "enumToString")
//    @Mapping(source = "performedBy.username", target = "performedBy")
//    @Mapping(source = "approvedBy.username", target = "approvedBy")
//    @Mapping(source = "createdAt", target = "createdAt")
//    TransactionHistoryResponseDto toHistory(Transaction txn);
//
//    @Mapping(source = "transactionRef", target = "transactionRef")
//    @Mapping(source = "account.accountNumber", target = "accountNumber")
//    @Mapping(source = "amount", target = "amount")
//    @Mapping(source = "performedBy.username", target = "performedBy")
//    @Mapping(source = "createdAt", target = "createdAt")
//    PendingApprovalResponseDto toPendingApproval(Transaction txn);
//
//    // helper for Enum → String
//    @Named("enumToString")
//    default String enumToString(Enum<?> value) {
//        return value != null ? value.name() : null;
//    }
//}
