import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-signup',
  standalone:false,
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {

  username = '';
  password = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  signup() {
    this.authService.signup({
      username: this.username,
      password: this.password
    }).subscribe({
      next: (res) => {
        if (res.success) {
          alert('Signup successful. Please login.');
          this.router.navigate(['/login']);
        } else {
          alert(res.message);
        }
      },
      error: () => alert('Server error')
    });
  }
}
