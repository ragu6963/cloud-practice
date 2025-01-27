import api from "./axios";

const postsApi = {
  getPosts: async () => {
    const resposne = await api.get("api/posts");
    return resposne.data;
  },

  getPost: async (postId) => {
    const resposne = await api.get(`api/posts/${postId}`);
    return resposne.data;
  },

  postPost: async (data) => {
    const resposne = await api.post(`api/posts`, data, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
    return resposne.data;
  },
  
  deletePost: async (postId) => {
    await api.delete(`api/posts/${postId}`);
  },
};

export default postsApi;
