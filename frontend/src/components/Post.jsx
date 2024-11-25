import React from "react";
import styles from "./Post.module.css"; // CSS 모듈 파일 가져오기
import { useNavigate } from "react-router-dom";

export default function Post({ id, title, content = null }) {
  const Navigate = useNavigate();
  return (
    <div className={styles.postsContainer}>
      <h2
        onClick={() => {
          Navigate(`/post/${id}`);
        }}
        className={styles.postTitle}
      >
        {title}
      </h2>
      <p className={styles.postContent}>{content}</p>
    </div>
  );
}
