import api from "./axios";

const postsApi = {
  getMessage: async () => {
    const resposne = await api.get();
    return resposne.data;
  },
  getPosts: async () => {
    const resposne = await api.get("api/posts");
    return resposne.data;
  },
  getPost: async (postId) => {
    const resposne = await api.get(`api/posts/${postId}`, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
    return resposne.data;
  },
  postPost: async (data) => {
    const resposne = await api.post(`api/posts`, data);
    return resposne.data;
  },
};

export default postsApi;
