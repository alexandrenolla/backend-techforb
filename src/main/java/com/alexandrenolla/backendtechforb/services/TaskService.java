package com.alexandrenolla.backendtechforb.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alexandrenolla.backendtechforb.models.Task;
import com.alexandrenolla.backendtechforb.models.User;
import com.alexandrenolla.backendtechforb.models.Enums.TransactionType;
import com.alexandrenolla.backendtechforb.repositories.TaskRepository;
import com.alexandrenolla.backendtechforb.services.exceptions.DataBindingViolationException;
import com.alexandrenolla.backendtechforb.services.exceptions.InsufficientBalanceException;
import com.alexandrenolla.backendtechforb.services.exceptions.ObjectNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task findById(Long id) {
        Optional<Task> task = this.taskRepository.findById(id);
        return task.orElseThrow(() -> new ObjectNotFoundException(
            "Task not found! Id: " + id + ", Type: " + Task.class.getName()));
        
    }

    public List<Task> findAllByUserId(Long userId) {

        List<Task> tasks = this.taskRepository.findByUser_Id(userId);
        
        return tasks;
    }

    @Transactional
    public Task create(Task obj) {
        User user = this.userService.findById(obj.getUser().getId());
        obj.setId(null);
        obj.setUser(user);
        obj = this.taskRepository.save(obj);
        return obj;
    }

    @Transactional
    public Task update(Task obj) {
        Task newObj = findById(obj.getId());
        newObj.setDescription(obj.getDescription());
        newObj.setValue(obj.getValue());
        return this.taskRepository.save(newObj);
    }

    public void delete(Long id) {
        findById(id);
        try {
            this.taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException("There are entities related!");
        }
    }

    @Transactional
    public Task deposit(Task depositTask) {
        User user = userService.findById(depositTask.getUser().getId());
        BigDecimal depositAmount = depositTask.getValue();
        user.setBalance(user.getBalance().add(depositAmount));
       
        depositTask.setDescription(TransactionType.DEPOSIT.name());
        depositTask.setValue(depositAmount);
        depositTask.setUser(user);
        depositTask.setId(null);
        return taskRepository.save(depositTask);
    }

    @Transactional
    public Task transfer(Task transferTask, Long recipientUserId) {
        User sender = userService.findById(transferTask.getUser().getId());
        User recipient = userService.findById(recipientUserId);

        BigDecimal transferAmount = transferTask.getValue();
        if (sender.getBalance().compareTo(transferAmount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance for transfer");
        }

        sender.setBalance(sender.getBalance().subtract(transferAmount));
        
        recipient.setBalance(recipient.getBalance().add(transferAmount));

        
        transferTask.setDescription(TransactionType.TRANSFER.name());
        transferTask.setValue(transferAmount);
        transferTask.setUser(sender);
        transferTask.setId(null);
        taskRepository.save(transferTask);

        
        Task recipientTask = new Task();
        recipientTask.setDescription(TransactionType.TRANSFER.name());
        recipientTask.setValue(transferAmount);
        recipientTask.setUser(recipient);
        recipientTask.setId(null);
        taskRepository.save(recipientTask);

        return transferTask;
    }

    @Transactional
    public Task withdraw(Task withdrawTask) {
        User user = userService.findById(withdrawTask.getUser().getId());
        BigDecimal withdrawalAmount = withdrawTask.getValue();
        if (user.getBalance().compareTo(withdrawalAmount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance for withdrawal");
        }

        // Deduz o valor da conta do usuário
        user.setBalance(user.getBalance().subtract(withdrawalAmount));
        // Configura a descrição da tarefa como "withdraw"
        withdrawTask.setDescription(TransactionType.WITHDRAW.name());
        withdrawTask.setValue(withdrawalAmount);
        withdrawTask.setUser(user);
        withdrawTask.setId(null);

        return taskRepository.save(withdrawTask);
    }



}