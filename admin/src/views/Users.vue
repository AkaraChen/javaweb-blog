<template>
  <div class="card">
    <div class="card-body">
      <div class="page-header page-header-border">
        <div class="row align-items-center">
          <div class="col">
            <h3 class="page-title">用户管理</h3>
          </div>
          <div class="col-auto">
            <div class="btn-list">
              <router-link to="/add/user" class="btn btn-blue">新增用户</router-link>
            </div>
          </div>
        </div>
      </div>
      <div v-for="item in meta" class="card" style="margin-bottom: 10px">
        <div class="card-body">
          <h3 class="card-title">
            <router-link :to="'/user/'+item.id">
              <i class="ti ti-user"></i>
              {{ item.name }}
              <i class="ti ti-id"></i>
              {{ item.id }}
            </router-link>
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
  name: "Users",
  setup() {
    const meta = ref(0)
    const store = useStore()
    const baseBackend = store.state.baseBackend
    axios.get(baseBackend + '/user/all').then(response => {
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