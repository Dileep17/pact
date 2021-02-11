import { render, screen } from '@testing-library/react';
import App from './App';

test('renders learn react link', () => {
  render(<App />);
  const searchField = screen.getByPlaceholderText(/search products/i);
  expect(searchField).toBeInTheDocument();
});
