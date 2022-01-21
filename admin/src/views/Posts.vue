<template>
  <div class="card">
    <div class="card-body">
      <div class="page-header page-header-border">
        <div class="row align-items-center">
          <div class="col">
            <h2 class="page-title">文章管理</h2>
          </div>
          <div class="col-auto">
            <div class="btn-list">
              <router-link to="/add/post" class="btn btn-blue">新建文章</router-link>
            </div>
          </div>
        </div>
      </div>
      <div v-for="item in meta" class="card" style="margin-bottom: 10px">
        <div class="card-body">
          <h3 class="card-title">
            <router-link :to="'/post/'+item.id">{{ item.title }}</router-link>
          </h3>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import {useStore} from "vuex"
import {ref} from 'vue'

export default {
  name: "Posts",
  setup() {
    const meta = ref(0)
    const store = useStore()
    const baseBackend = store.state.baseBackend
    axios.get(baseBackend + '/post/list?size=9999').then(response => {
      meta.value = response.data
    })
    return {baseBackend, meta}
  },
}
</script>

<style scoped>
.page-header-border {
  border-bottom: none !important;
  padding-bottom: 10px !important;
}
</style>