<template>
  <div class="mb-3">
    <header class="navbar navbar-expand-md navbar-light d-print-none">
      <div class="container-xl">
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbar-menu">
          <span class="navbar-toggler-icon"></span>
        </button>
        <h1 class="navbar-brand navbar-brand-autodark d-none-navbar-horizontal pe-0 pe-md-3">
          <a href="/">
            <img src="https://preview.tabler.io/static/logo.svg" width="32" height="32" alt="Tabler"
                 class="navbar-brand-image me-3">
          </a>
        </h1>
        <div class="navbar-nav flex-row order-md-last">
          <div class="nav-item d-none d-md-flex me-3">
            <div class="btn-list">
              <a href="https://github.com/tabler/tabler" class="btn" target="_blank" rel="noreferrer">
                <i class="ti ti-brand-github icon"></i>Source code
              </a>
              <a href="https://github.com/sponsors/codecalm" class="btn" target="_blank" rel="noreferrer">
                <i class="ti ti-heart icon"></i>Sponsor
              </a>
            </div>
          </div>
        </div>
      </div>
    </header>
    <div class="navbar-expand-md">
      <div class="collapse navbar-collapse" id="navbar-menu">
        <div class="navbar navbar-light">
          <div class="container-xl">
            <ul class="navbar-nav">
              <li v-for="item in categoryList" class="nav-item">
                <a class="nav-link" href="./#">
                  <span class="nav-link-icon d-md-none d-lg-inline-block">
                    <i class="ti ti-star icon"></i>
                  </span>
                  <span class="nav-link-title">{{ item.name }}</span>
                </a>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {useStore} from "vuex";
import axios from "axios";
import {ref} from "vue";

export default {
  name: 'Navbar',
  setup() {
    const store = useStore()
    const baseBackend = store.state.baseBackend
    const categoryList = ref(0)
    axios.get(baseBackend + '/category/all').then(response => {
      categoryList.value = response.data
    })
    return {categoryList}
  }
}
</script>