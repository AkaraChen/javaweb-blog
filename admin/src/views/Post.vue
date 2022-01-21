<template>
  <div class="card">
    <div class="card-body">
      <h3 class="card-title">{{ meta.title }}</h3>
      <textarea class="form-control" data-bs-toggle="autosize" v-model="meta.content"></textarea>
      <div class="btn-group" style="margin-top: 10px">
        <a @click="update" class="btn btn-blue">发布</a>
        <a @click="del" class="btn btn-danger">删除</a>
      </div>
    </div>
  </div>
</template>

<script>
import {useStore} from "vuex";
import axios from "axios";
import {useRoute} from 'vue-router'
import {ref} from "vue";

export default {
  name: "Post",
  setup() {
    const store = useStore()
    const route = useRoute()
    const meta = ref(0)
    const baseBackend = store.state.baseBackend
    axios.get(baseBackend + '/post/get?id=' + route.params.id).then(response => {
      meta.value = response.data
    })
    return {meta, baseBackend}
  },
  methods: {
    update() {
      if (confirm("确定要发布吗")) {
        axios({
          method: 'POST',
          url: this.baseBackend + '/post/update',
          params: {
            id: this.$route.params.id,
            content: this.meta.content
          }
        })
      }

    },
    del() {
      if (confirm("确定要删除吗？")) {
        axios({
          method: 'POST',
          url: this.baseBackend + '/post/del',
          params: {
            id: this.$route.params.id
          }
        })
      }
    }
  }
}
</script>

<style scoped>
textarea {
  height: 500px
}
</style>