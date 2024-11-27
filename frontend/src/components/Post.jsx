import React from "react";
import styles from "./Post.module.css"; // CSS 모듈 파일 가져오기
import { useNavigate } from "react-router-dom";

export default function Post({ id, post, isDetail }) {
  const Navigate = useNavigate();
  return (
    <div className={styles.postsContainer}>
      <h2
        onClick={() => {
          Navigate(`/post/${post.id}`);
        }}
        className={styles.postTitle}
      >
        {post.title}
      </h2>
      {isDetail && (
        <>
          <p className={styles.postContent}>{post.content}</p>
          <img src={post.imageUrl} alt="" />
        </>
      )}
    </div>
  );
}
