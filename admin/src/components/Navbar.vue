<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
      <router-link class="navbar-brand" to="/posts">后台管理</router-link>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
              aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
          <li class="nav-item">
            <router-link class="nav-link" to="/posts">文章管理</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/users">用户管理</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/setting">系统设置</router-link>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script>
export default {
  name: "Navbra"
}
</script>

<style scoped>
.navbar {
  background-color: #fff !important;
}
</style>