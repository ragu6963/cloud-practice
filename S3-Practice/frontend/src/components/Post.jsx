import styles from "./Post.module.css";
import { useNavigate } from "react-router-dom";
import postsApi from "../api/postsApi";

export default function Post({ post, isDetail }) {
  const Navigate = useNavigate();

  async function onClick() {
    try {
      await postsApi.deletePost(post.id);
      Navigate("/");
    } catch (error) {
      console.error("ERROR : ", error);
    }
  }
  return (
    <div className={styles.postsContainer}>
      <h2
        onClick={() => {
          Navigate(`/post/${post.id}`);
        }}
        className={`${styles.postTitle} ${!isDetail && styles.pointer}`}
      >
        {post.title}
      </h2>
      {isDetail && (
        <>
          <p className={styles.postContent}>{post.content}</p>
          <img src={post.imageUrl} />
          <p>{post.originalFileName}</p>
          <button onClick={onClick}>삭제</button>
        </>
      )}
    </div>
  );
}
