package com.askrindo.spesification;

import com.askrindo.dto.ProjectSearchDTO;
import com.askrindo.entity.Project;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

//@RequestParam(name = "divId", required = false) String divId,
//@RequestParam(name = "catAct", required = false) String catAct,
//@RequestParam(name = "pm", required = false) String pm,
//@RequestParam(name = "pmo", required = false) String pmo,
//@RequestParam(name = "copm", required = false) String copm,
//@RequestParam(name = "status", required = false) String status){


public class ProjectSpesification {
    public static Specification<Project> getSpesification(ProjectSearchDTO projectSearchDTO){
        return new Specification<Project>(){
            @Override
            public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(projectSearchDTO.getDivisiUser()!=null){
                    Predicate productNamePredicate = criteriaBuilder.like(root.get("divisiUser"), "%"+projectSearchDTO.getDivisiUser()+"%");
                    predicates.add(productNamePredicate);
                }

                if(projectSearchDTO.getCatAct()!=null){
                    Predicate productNamePredicate = criteriaBuilder.like(root.get("categoryActivity"), "%"+projectSearchDTO.getCatAct()+"%");
                    predicates.add(productNamePredicate);
                }

                if(projectSearchDTO.getPm()!=null){
                    Predicate productNamePredicate = criteriaBuilder.like(root.get("pm"), "%"+projectSearchDTO.getPm()+"%");
                    predicates.add(productNamePredicate);
                }

                if(projectSearchDTO.getPmo()!=null){
                    Predicate productNamePredicate = criteriaBuilder.like(root.get("pmo"), "%"+projectSearchDTO.getPmo()+"%");
                    predicates.add(productNamePredicate);
                }

                if(projectSearchDTO.getCopm()!=null){
                    Predicate productNamePredicate = criteriaBuilder.like(root.get("coPM"), "%"+projectSearchDTO.getCopm()+"%");
                    predicates.add(productNamePredicate);
                }

                if(projectSearchDTO.getStatus()!=null){
                    Predicate productNamePredicate = criteriaBuilder.like(root.get("statusProject"), "%"+projectSearchDTO.getStatus()+"%");
                    predicates.add(productNamePredicate);
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}