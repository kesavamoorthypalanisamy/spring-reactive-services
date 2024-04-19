package com.hippo.userservice.util;

import java.time.LocalDateTime;
import org.springframework.beans.BeanUtils;
import com.hippo.userservice.entity.User;
import com.hippo.userservice.entity.UserTransaction;
import com.hippo.userservice.models.TransactionRequestDto;
import com.hippo.userservice.models.TransactionResponseDto;
import com.hippo.userservice.models.TrasactionStatus;
import com.hippo.userservice.models.UserDto;
import com.hippo.userservice.models.UserTransactionsDto;

public class EntityMapperUtil {
    public static UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }

    public static User toUser(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        return user;
    }

    public static UserTransaction toTransactionEntity(TransactionRequestDto dto) {
        UserTransaction userTransaction = new UserTransaction();
        userTransaction.setUserId(dto.getUserId());
        userTransaction.setAmount(dto.getAmount());
        userTransaction.setTransactionDate(LocalDateTime.now());
        return userTransaction;
    }

    public static TransactionResponseDto toTrasactionResponseDto(TransactionRequestDto userTransaction,
            TrasactionStatus status) {
        TransactionResponseDto trasactionResponseDto = new TransactionResponseDto();
        trasactionResponseDto.setAmount(userTransaction.getAmount());
        trasactionResponseDto.setStatus(status);
        trasactionResponseDto.setUserId(userTransaction.getUserId());
        return trasactionResponseDto;
    }

    public static UserTransactionsDto toUserTransactionsDto(UserTransaction transaction) {
        UserTransactionsDto transactionsDto = new UserTransactionsDto();
        transactionsDto.setTransactionDate(transaction.getTransactionDate());
        transactionsDto.setAmount(transaction.getAmount());
        return transactionsDto;
    }
    
}
