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
          valid: true,
          success: false,
          message: '',
          active: false,
          nameRules: [
                  v => !!v || 'Name is required',
                  v => (v && v.length <= 10) || 'Name must be less than 10 characters',
                ],
          email: '',
          emailRules: [
                v => !!v || 'E-mail is required',
                v => /.+@.+\..+/.test(v) || 'E-mail must be valid',
              ],
              select: null,
              items: [
                'Item 1',
                'Item 2',
                'Item 3',
                'Item 4',
              ],
          checkbox: false,
          show1: false,
          password: 'Password',
          rules: {
                    required: value => !!value || 'Required.',
                    min: v => v.length >= 8 || 'Min 8 characters',
                    emailMatch: () => (`The email and password you entered don't match`),
                  },
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
              resetValidation () {
                this.$refs.form.resetValidation()
              },
        }
      })