namespace DeveloperExerciseApi
{
    public class NamedService<T>
    {
        public string Name { get; }
        public T Service { get; }

        public NamedService(string name, T service)
        {
            Name = name;
            Service = service;
        }
    }
}
