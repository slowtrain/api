package com.itmsg.episode.common.files;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    Attachment findByAttachmentId(String attachmentId);
    List<Attachment> findByOwnertableAndOwnerid(String ownertable, Long ownerid, Sort sort);
}