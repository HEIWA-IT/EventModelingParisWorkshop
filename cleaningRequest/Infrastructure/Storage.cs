using System;
using System.IO;
using Newtonsoft.Json;

namespace Infrastructure
{
    public class Storage<T>
    {
        private readonly string pattern;
        private readonly Func<T, object> keyProp;

        public Storage(string pattern, Func<T, object> keyProp)
        {
            this.pattern = pattern;
            this.keyProp = keyProp;
        }

        public void Save(T model)
        {
            var path = string.Format(pattern, keyProp(model));
            if (File.Exists(path)) File.Delete(path);
            File.WriteAllText(path, JsonConvert.SerializeObject(model));
        }

        public T Read(object key)
        {
            var path = string.Format(pattern, key);
            if (!File.Exists(path)) return default;
            return JsonConvert.DeserializeObject<T>(File.ReadAllText(path));
        }
    }
}