import axios from "axios";
import { ChangeEvent, Component, FormEvent } from "react";

const API_URL = "https://jsonplaceholder.typicode.com/users"; // Simule une API externe

interface IUser {
  id: number;
  name: string;
  username: string;
  email: string;
  address: {
    street: string;
    suite: string;
    city: string;
    zipcode: string;
    geo: {
      lat: string;
      lng: string;
    };
  };
  phone: string;
  website: string;
  company: {
    name: string;
    catchPhrase: string;
    bs: string;
  };
}

interface TodoState {
  users: IUser[];
}

export default class TodoPage extends Component<object, TodoState> {
  constructor(props: object) {
    super(props);
    this.state = {
      users: [],
    };
  }

  componentDidMount() {
    if (this.state.users.length == 0) this.loadUsers();
  }

  loadUsers = async () => {
    try {
      const response = await axios.get<IUser[]>(API_URL);
      this.setState({ users: response.data });
    } catch (error) {
      console.error("Erreur lors du chargement des utilisateurs:", error);
    }
  };

  addUser = async (user: Omit<IUser, "id">) => {
    try {
      const res = await axios.post(API_URL, user);
      this.setState((prev) => ({
        users: [res.data, ...prev.users],
      }));
    } catch (error) {
      console.error("Erreur lors du chargement des utilisateurs:", error);
    }
  };

  updateUser = async (updatedUser: IUser,) => {
    try {
      const res = await axios.patch(`${API_URL}/${updatedUser.id}`, updatedUser);
      this.setState((prev) => ({
        users: prev.users.map((user) =>
          user.id === updatedUser.id ? updatedUser : user
        ),
      }));
    } catch (error) {
      console.error("Erreur lors du chargement des utilisateurs:", error);
    }
  };

  deleteUser = async (id: number) => {
    const confirmDelete = window.confirm("Êtes-vous sûr de vouloir supprimer cet utilisateur ?");
    if (!confirmDelete) return;


    try {
      await axios.delete(`${API_URL}/${id}`);
      this.setState((prev) => ({
        users: prev.users.filter((user) => user.id !== id),
      }));
    } catch (error) {
      console.error("Erreur lors du chargement des utilisateurs:", error);
    }



  };

  render() {
    return (
      <div>
        <h1>User Management</h1>
        <UserForm addUser={this.addUser} />
        <UserList
          users={this.state.users}
          updateUser={this.updateUser}
          deleteUser={this.deleteUser}
        />
      </div>
    );
  }
}

interface UserListProps {
  users: IUser[];
  updateUser: (user: IUser) => void;
  deleteUser: (id: number) => void;
}

class UserList extends Component<UserListProps> {
  render() {
    const { users, updateUser, deleteUser } = this.props;
    return (
      <ul>
        {users.map((user) => (
          <UserItem
            key={user.id}
            user={user}
            updateUser={updateUser}
            deleteUser={deleteUser}
          />
        ))}
      </ul>
    );
  }
}

interface UserItemProps {
  user: IUser;
  updateUser: (user: IUser) => void;
  deleteUser: (id: number) => void;
}
interface UserItemState {
  isEditing: boolean;
  name: string;
  email: string;
}
class UserItem extends Component<UserItemProps, UserItemState> {
  constructor(props: UserItemProps) {
    super(props);
    this.state = {
      isEditing: false,
      name: props.user.name,
      email: props.user.email,
    };
  }

  handleEdit = () => {
    this.setState({ isEditing: true });
  };

  handleSave = () => {
    const { user, updateUser } = this.props;
    const { name, email } = this.state;

    updateUser({ ...user, name, email });
    this.setState({ isEditing: false });
  };

  handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    this.setState({ [e.target.name]: e.target.value } as Pick<
      UserItemState,
      keyof UserItemState
    >);
  };

  render() {
    const { user, deleteUser } = this.props;
    const { isEditing, name, email } = this.state;

    return (
      <li>
        {isEditing ? (
          <>
            <input
              type="text"
              name="name"
              value={name}
              onChange={this.handleChange}
              data-testid={`name-user-${user.id}`}
            />
            <input
              type="email"
              name="email"
              value={email}
              onChange={this.handleChange}
              data-testid={`email-user-${user.id}`}
            />
            <button onClick={this.handleSave}
            data-testid={`save-user-${user.id}`}>Enregistrer</button>
          </>
        ) : (
          <>
            <p data-testid={`user-${user.id}`}>
              <strong>{user.name}</strong> - {user.email}
            </p>
            <button onClick={this.handleEdit}
            data-testid={`edit-user-${user.id}`}>Modifier</button>
            <button onClick={() => deleteUser(user.id)}
              data-testid={`delete-user-${user.id}`}>Supprimer</button>
          </>
        )}
      </li>
    );
  }
}


interface UserFormProps {
  addUser: (user: Omit<IUser, "id">) => void;
}
interface UserFormState {
  name: string;
  email: string;
}

class UserForm extends Component<UserFormProps, UserFormState> {
  constructor(props: UserFormProps) {
    super(props);
    this.state = {
      name: "",
      email: "",
    };
  }

  handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    this.setState({ [e.target.name]: e.target.value } as Pick<
      UserFormState,
      keyof UserFormState
    >);
  };

  handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const { name, email } = this.state;
    if (!name || !email) return;

    const newUser: Omit<IUser, "id"> = {
      name,
      email,
      username: email.split("@")[0],
      address: {
        street: "Kulas Light",
        suite: "Apt. 556",
        city: "Gwenborough",
        zipcode: "92998-3874",
        geo: {
          lat: "-37.3159",
          lng: "81.1496",
        },
      },
      phone: "1-770-736-8031 x56442",
      website: "hildegard.org",
      company: {
        name: "Romaguera-Crona",
        catchPhrase: "Multi-layered client-server neural-net",
        bs: "harness real-time e-markets",
      },
    };

    this.props.addUser(newUser);
    this.setState({ name: "", email: "" });
  };

  render() {
    return (
      <form onSubmit={this.handleSubmit}>
        <input
          type="text"
          name="name"
          placeholder="Name"
          value={this.state.name}
          onChange={this.handleChange}
          required={true}
        />
        <input
          type="email"
          name="email"
          placeholder="Email"
          value={this.state.email}
          onChange={this.handleChange}
          required={true}
        />
        <button type="submit" data-testid="add-user">Ajouter</button>
      </form>
    );
  }
}