<template>
  <div class="card">
    <div class="card-body">
      <div class="row">
        <div class="col-9">
          <h3 class="card-title">攥写文章</h3>
          <input placeholder="文章标题" type="text" class="form-control" v-model="title" style="margin-bottom: 10px">
          <textarea class="form-control content-edit" data-bs-toggle="autosize" v-model="content"></textarea>
          <div class="btn-group" style="margin-top: 10px">
            <a @click="release" class="btn btn-blue">发布</a>
          </div>
        </div>
        <div class="col-3">
          <div class="card" style="margin-bottom: 10px">
            <div class="card-body">
              <h3 class="card-title">选择用户</h3>
              <div class="btn-list">
                <a @click="author = item.id;authorId = item.name" v-for="item in userlist" class="btn">{{
                    item.name
                  }}</a>
              </div>
              <p style="margin-top: 10px">当前选择：{{ authorId }}</p>
            </div>
          </div>
          <div class="card">
            <div class="card-body">
              <h3 class="card-title">选择分类</h3>
              <div class="btn-list">
                <a @click="category = item.id;categoryId = item.name" class="btn" v-for="item in categorylist">
                  {{ item.name }}</a>
              </div>
              <p style="margin-top: 10px">当前选择：{{ categoryId }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import {useStore} from "vuex";
import {ref} from "vue";

export default {
  name: "PostAdd",
  setup() {
    const baseBackend = useStore().state.baseBackend
    const author = ref(1)
    const key = ref(0)
    const content = ref(undefined)
    const category = ref(0)
    const title = ref(undefined)
    return {author, key, content, category, title, baseBackend}
  },
  data() {
    return {
      userlist: [{name: "test", id: 1}],
      categorylist: [{name: "test", id: 1}],
      authorId: 'akarachen',
      categoryId: '学习强国',
    }
  },
  created() {
    axios.get(this.baseBackend + "/user/all").then(response => {
      this.userlist = response.data
    }).catch(error => {
      console.log(error)
    })
    axios.get(this.baseBackend + "/category/all").then(response => {
      this.categorylist = response.data
    }).catch(error => {
      console.log(error)
    })
  },
  methods: {
    release() {
      if (confirm("确定要发布吗")) {
        axios({
          method: 'POST',
          url: this.baseBackend + '/post/add',
          params: {
            content: this.content,
            category: this.category,
            author: this.author,
            key: this.author,
            title: this.title,
          }
        })
      }
    },
  }
}
</script>

<style scoped>
.content-edit {
  height: 500px
}
</style>