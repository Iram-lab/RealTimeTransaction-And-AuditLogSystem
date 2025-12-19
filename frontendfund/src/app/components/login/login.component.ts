import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  username = '';
  password = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  login() {
    this.authService.login({
      username: this.username,
      password: this.password
    }).subscribe({
      next: (res) => {
        if (res.success) {
          this.authService.saveAuth(res.data.token, res.data.userId);
          this.router.navigate(['/transfer']);
        } else {
          alert(res.message);
        }
      },
      error: () => alert('Server error')
    });
  }

  goToSignup() {
    this.router.navigate(['/signup']);
  }
}
