<template>
  <AppHeaderDropdown right no-caret>
    <template slot="header">
      <!-- <img src="../img/avatars/6.jpg" class="img-avatar"alt="admin@bootstrapmaster.com" /> -->
      <b>{{displayName}}</b>
    </template>
    <template slot="dropdown">
      <b-dropdown-item><i class="fa fa-lock" /> <a v-on:click="signOut">Signout</a></b-dropdown-item>
      <b-dropdown-item><i class="fa fa-lock" /> <a v-on:click="pwChange">Change Password</a></b-dropdown-item>
      <!--
      <b-dropdown-header tag="div" class="text-center">
        <strong>Settings</strong>
      </b-dropdown-header>
      <b-dropdown-item><i class="fa fa-lock" /> <a v-on:click="signOut">Signout</a></b-dropdown-item>
      <b-dropdown-item><i class="fa fa-user" /> Profile</b-dropdown-item>
      <b-dropdown-item><i class="fa fa-wrench" /> Settings</b-dropdown-item>
      <b-dropdown-header tag="div" class="text-center"><strong>Account</strong></b-dropdown-header>
      <b-dropdown-item><i class="fa fa-bell-o" /> Updates
        <b-badge variant="info">{{ itemsCount }}</b-badge>
      </b-dropdown-item>
      <b-dropdown-item><i class="fa fa-envelope-o" /> Messages
        <b-badge variant="success">{{ itemsCount }}</b-badge>
      </b-dropdown-item>
      <b-dropdown-item><i class="fa fa-tasks" /> Tasks
        <b-badge variant="danger">{{ itemsCount }}</b-badge>
      </b-dropdown-item>
      <b-dropdown-item><i class="fa fa-comments" /> Comments
        <b-badge variant="warning">{{ itemsCount }}</b-badge>
      </b-dropdown-item>
      <b-dropdown-item><i class="fa fa-usd" /> Payments
        <b-badge variant="secondary">{{ itemsCount }}</b-badge>
      </b-dropdown-item>
      <b-dropdown-item><i class="fa fa-file" /> Projects
        <b-badge variant="primary">{{ itemsCount }}</b-badge>
      </b-dropdown-item>
      <b-dropdown-item><i class="fa fa-shield" /> Lock Account</b-dropdown-item>
      <b-dropdown-divider />
      -->
    </template>
  </AppHeaderDropdown>
</template>

<script>
import { HeaderDropdown as AppHeaderDropdown } from '@coreui/vue'
export default {
  name: 'DefaultHeaderDropdownAccnt',
  components: {
    AppHeaderDropdown
  },
  data: () => ({
    itemsCount: 42,
    displayName: null
  }),
  methods: {
    signOut () {
      /* SignOut by EPISODE JWT START */
      this.$http.post(
        '/episode/api/authentication/signout'
      ).then((res) => {
        // 로그인 페이지 사용 유무
        this.$router.push('/gate')
        this.$store.commit('toEmpty')
        this.$cookie.delete('kaiicamsecret')
        // window.location.href = 'http://sso.koreaaero.com/'
      }).catch((error) => {
        console.log(error)
      })
    },
    pwChange () {
      this.$emit('changePwOpen')
    }
  },
  created () {
    let user = this.$store.getters.getUser
    this.displayName = user.firstname
  }
}
</script>
