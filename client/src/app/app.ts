import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { LoginPage } from './login-page/login-page';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, LoginPage],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected title = 'banana';
}
