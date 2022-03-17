Vue.component("env-div", {
  props: ['env'],
  template: `
    <v-divider class="mx-4"></v-divider>
    <div>
      <p> branch </p>
      <p> {{env.commitHash}} </p>
    </div>
    `
});

var app = new Vue({
        el: '#app-login',
        vuetify: new Vuetify(),
        props: {
            params: {
                type: Object,
                default: {branch: 'branch', commitHash: 'commit hash'}
            }
        },
        data: {
          alert1: false,
          loginError: '',
          valid: true,
          success: false,
          message: '',
          active: false,
          email: 'admin@autolink.io',
          emailRules: [
                v => !!v || 'E-mail is required',
                v => /.+@.+\..+/.test(v) || 'E-mail must be valid',
              ],
          show1: false,
          password: 'admin',
          passwordRules: [
                  v => !!v || 'Password is required'
                ],
          env11: {branch: 'branch', commitHash: 'commit hash'},
        },
        beforeMount: function () {
          this.params = JSON.parse(this.$el.attributes.params.value);
        },
        methods: {
              validate () {
                this.$refs.form.validate()
              },
              reset () {
                this.$refs.form.reset()
              },
              login() {

                nativeForm.submit()

                /*let self = this;

                const json = JSON.stringify({ email: self.email, password: self.password });

                axios.post('/login', json, {
                   headers: {
                     'Content-Type': 'application/json'
                   }
                 }).then(function (response) {

                    self.loginError = response.data.message;
                    self.alert1 = true;
                    console.log(response);

                    window.location = "/dashboard"

                  })
                  .catch(function (error) {
                    self.loginError = error;
                    self.alert1 = true;
                    console.log(error);
                  });*/

              }
        }
      })