package ru.pgk.smartbudget.features.goal.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.smartbudget.common.exceptions.ResourceNotFoundException;
import ru.pgk.smartbudget.features.goal.GoalRepository;
import ru.pgk.smartbudget.features.goal.dto.params.CreateOrUpdateGoalParams;
import ru.pgk.smartbudget.features.goal.dto.params.GetGoalsParams;
import ru.pgk.smartbudget.features.goal.entities.GoalEntity;
import ru.pgk.smartbudget.features.goal.specifications.GoalSpecifications;
import ru.pgk.smartbudget.features.user.entities.UserEntity;
import ru.pgk.smartbudget.features.user.services.UserService;

@Service
@RequiredArgsConstructor
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;

    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public GoalEntity getById(Long id) {
        return goalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GoalEntity> getAll(GetGoalsParams params) {
        Specification<GoalEntity> spec = Specification.where(null);
        Pageable pageable = PageRequest.of(params.getOffset(), params.getLimit());

        if(params.getUserId() != null) {
            spec = spec.and(GoalSpecifications.byUserId(params.getUserId()));
        }

        if(params.getSearch() != null && !params.getSearch().isEmpty()) {
            spec = spec.and(GoalSpecifications.likeByName(params.getSearch()));
        }

        if(params.getIsAchieved() != null) {
            spec = spec.and(GoalSpecifications.byIsAchieved(params.getIsAchieved()));
        }

        if(params.getDeadlineStartDate() != null) {
            spec = spec.and(GoalSpecifications.deadlineGreaterThanOrEqual(params.getDeadlineStartDate()));
        }

        if(params.getDeadlineEndDate() != null) {
            spec = spec.and(GoalSpecifications.deadlineLessThanOrEqual(params.getDeadlineEndDate()));
        }

        if(params.getOrderByType() != null) {
            Sort sort = Sort.by(params.getOrderByType().getFieldName());
            if (params.getOrderByType().isDescending()) {
                sort = sort.descending();
            }
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        }

        return goalRepository.findAll(spec, pageable);
    }

    @Override
    @Transactional
    public GoalEntity create(Long userId, CreateOrUpdateGoalParams params) {
        UserEntity user = userService.getById(userId);
        GoalEntity goal = new GoalEntity();
        goal.setUser(user);
        setParams(goal, params);
        return goalRepository.save(goal);
    }

    @Override
    @Transactional
    public GoalEntity update(Long id, CreateOrUpdateGoalParams params) {
        GoalEntity goal = getById(id);
        setParams(goal, params);
        return goalRepository.save(goal);
    }

    @Override
    @Transactional
    public void updateCurrentAmount(Long id, Double amount) {
        GoalEntity goal = getById(id);
        goal.setCurrentAmount(amount);
        goalRepository.save(goal);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        goalRepository.deleteById(id);
    }

    private void setParams(GoalEntity goal, CreateOrUpdateGoalParams params) {
        goal.setName(params.getName());
        goal.setDeadline(params.getDeadline());
        goal.setIsAchieved(params.getIsAchieved());
        goal.setTargetAmount(params.getTargetAmount());
        goal.setCurrentAmount(params.getCurrentAmount());
    }
}
