package com.frogsoft.frogsoftcms.repository.other;

import com.frogsoft.frogsoftcms.model.other.Other;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtherRepository extends JpaRepository<Other, String> {

  Other findByKey(String key);
}
