package com.frogsoft.frogsoftcms.repository.history;

import com.frogsoft.frogsoftcms.model.history.History;
import com.frogsoft.frogsoftcms.model.user.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long> {

  List<History> findAllByUser(User user);

  Page<History> findAllByUser(User user,Pageable pageable);
}
